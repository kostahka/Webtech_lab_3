package by.bsuir.lab3.server.service;

import by.bsuir.lab3.server.dao.DaoFactory;
import by.bsuir.lab3.server.model.Record;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

public class RecordService {
    private static final RecordService INSTANCE = new RecordService();

    private RecordService(){}
    public static RecordService getInstance(){
        return INSTANCE;
    }
    public Record createRecord(NodeList nodes){
        int id = 0;
        String first = "";
        String last = "";

        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                String text = nodes.item(i).getTextContent();
                switch (nodes.item(i).getNodeName()) {
                    case "id": id = Integer.parseInt(text);
                    break;
                    case "firstName": first = text;
                    break;
                    case "lastName": last = text;
                    break;
                    default: throw new IllegalArgumentException("No such case exists");
                }
            }
        }

        return new Record(id, first, last);
    }
    public List<Record> getAll(){
        return DaoFactory.getInstance().getRecordDao().getAll();
    }
    public Element createNode(Document doc, Record _case) {
        Element e = doc.createElement("case");
        Element id = doc.createElement("id");
        Element first = doc.createElement("firstName");
        Element last = doc.createElement("lastName");
        id.appendChild(doc.createTextNode(String.valueOf(_case.getId())));
        first.appendChild(doc.createTextNode(_case.getFirstName()));
        last.appendChild(doc.createTextNode(_case.getLastName()));
        e.appendChild(id);
        e.appendChild(first);
        e.appendChild(last);
        return e;
    }
    public boolean containsKey(int id){
        return DaoFactory.getInstance().getRecordDao().containsKey(id);
    }
    public void editRecord(int id, String firstName, String lastName){
        DaoFactory.getInstance().getRecordDao().editRecord(id, firstName, lastName);
    }
    public void addRecord(String firstName, String lastName) {
        DaoFactory.getInstance().getRecordDao().add(new Record(0, firstName, lastName));
    }
}
