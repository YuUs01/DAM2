package Actividad2T3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.print.Doc;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class InsertarClienteXML {
    public static void main(String[] args) {

        String fich;
        if (args.length < 1){
            System.out.println("Introduzca el documento XML en el que quiera insertan un cliente");
            return;
        }else{
             fich = args[0];
        }

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(fich));
            Element root = doc.getDocumentElement();

            Element nuevoCliente = doc.createElement("cliente");

            Element dni = doc.createElement("dni");
            dni.setTextContent("12345678Z");
            nuevoCliente.appendChild(dni);

            Element apellidos = doc.createElement("apellidos");
            apellidos.setTextContent("GOMEZ MOLINOS");
            nuevoCliente.appendChild(apellidos);

            Element cp = doc.createElement("cp");
            cp.setTextContent("28010");
            nuevoCliente.appendChild(cp);

            root.insertBefore(nuevoCliente, root.getFirstChild());

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.transform(new DOMSource(doc), new StreamResult(System.out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
