package by.bsuir.lab3.server.dao;

import by.bsuir.lab3.server.model.Record;
import by.bsuir.lab3.server.service.ServiceFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RecordDao {
    private static final RecordDao INSTANCE = new RecordDao();
    private static final String RECORDS_PATH = "records.xml";

    private final ReadWriteLock lock;
    private final Map<Integer, Record> records;

    private RecordDao() {
        lock = new ReentrantReadWriteLock();
        records = new HashMap<>();
        init();
    }
    public static RecordDao getInstance() {
        return INSTANCE;
    }
    private void init() {
        File file = new File(RECORDS_PATH);
        if(!file.exists()){
            try {
                file.createNewFile();
                update();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(RECORDS_PATH));
            doc.getDocumentElement().normalize();
            NodeList nodes = doc.getDocumentElement().getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
                Node node = nodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Record record = ServiceFactory.getInstance().getRecordService().createRecord(node.getChildNodes());
                    records.put(record.getId(), record);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
    public List<Record> getAll() {
        try {
            lock.readLock().lock();
            return new ArrayList<>(records.values());
        } finally {
            lock.readLock().unlock();
        }
    }
    public void add(Record record) {
        try {
            lock.writeLock().lock();
            if(records.size() > 0)
                record.setId(records.keySet().stream().max(Comparator.comparingInt(a -> a)).get() + 1);
            records.put(record.getId(), record);
            update();
        } finally {
            lock.writeLock().unlock();
        }
    }
    private void update() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element rootEle = doc.createElement("records");
            for(Record record : getAll()) {
                Element recordEle = ServiceFactory.getInstance().getRecordService().createNode(doc, record);
                rootEle.appendChild(recordEle);
            }

            doc.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(RECORDS_PATH)));

            } catch (IOException | TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    public boolean containsKey(int id){
        return records.containsKey(id);
    }
    public void editRecord(int id, String firstName, String lastName){
        Record updateRecord = new Record(id, firstName, lastName);
        try {
            lock.writeLock().lock();
            records.put(id, updateRecord);
            update();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
