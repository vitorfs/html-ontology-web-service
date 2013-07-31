/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vitorfs.ws;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.vitorfs.model.Tag;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.ejb.Stateless;

/**
 *
 * @author vitorfs
 */
@WebService(serviceName = "HtmlService")
@Stateless()
public class HtmlService {
    private static final String BASE_URI = "http://www.vitorfs.com/ontology/html.owl#";
    private static final String ONTOLOGY_URI = "file:///Users/vitorfs/NetBeansProjects/HtmlOntologyWebService/html_2.owl";
    private OntModel model;
    
    public HtmlService() {
        OntModel ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
        ontModel.read(ONTOLOGY_URI);
        Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
        reasoner = reasoner.bindSchema(ontModel);
        OntModelSpec ontModelSpec = OntModelSpec.OWL_DL_MEM;
        ontModelSpec.setReasoner(reasoner);
        model = ModelFactory.createOntologyModel(ontModelSpec, ontModel);
    }
    
    @WebMethod(operationName = "listClasses")
    public List<Tag> listClasses() {
        List<Tag> tags = new ArrayList<Tag>();
        try {
            OntModel inf = ModelFactory.createOntologyModel();
            InputStream in = FileManager.get().open(ONTOLOGY_URI);
            if (in == null) {
                throw new IllegalArgumentException("File: " + ONTOLOGY_URI + " not found");
            }
            inf.read(in, "");
            ExtendedIterator classes = inf.listNamedClasses();
            while (classes.hasNext()) {
                OntClass oc = (OntClass) classes.next();
                Tag tag = new Tag();
                
                tag.setName(oc.getLocalName());
                if (oc.getEquivalentClass() != null && oc.getEquivalentClass().getLocalName() != null)
                    tag.setEquiv(oc.getEquivalentClass().getLocalName());
                tags.add(tag);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return tags;
    }
    
    @WebMethod(operationName = "obterSubclasses")
    public List<Tag> obterSubclasses(String classe) {
        List<Tag> tags = new ArrayList<Tag>();
        String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
        "select ?Classe "+
        "where { "+
        "?Classe rdfs:subClassOf <" + BASE_URI + classe + "> "+
        "} \n ";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource r = qs.getResource("?Classe");
            Tag tag = new Tag();
            if (r.getLocalName() != null) {
                tag.setName(r.getLocalName());
                tag.setNameSpace(r.getNameSpace());
                tag.setUri(r.getURI());
                tags.add(tag);
            }
        }
        qe.close();
        return tags;
    }
    
    @WebMethod(operationName = "validarSemantica")
    public boolean validarSemantica(String tagName, String tipo) {
        boolean retorno = false;
        String queryString = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
        "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "+
        "select ?Classe "+
        "where { "+
        "?Classe rdfs:subClassOf <" + BASE_URI + tipo + "> "+
        "} \n ";

        Query query = QueryFactory.create(queryString);
        QueryExecution qe = QueryExecutionFactory.create(query, model);
        com.hp.hpl.jena.query.ResultSet results = qe.execSelect();
        while (results.hasNext()) {
            QuerySolution qs = results.next();
            Resource r = qs.getResource("?Classe");
            if (r.getLocalName() != null) {
                if (r.getLocalName().toUpperCase().equals(tagName.toUpperCase())) {
                    retorno = true;
                }
            }
        }
        qe.close();
        return retorno;
    }
    
    public static void main(String[] args){
        HtmlService hs = new HtmlService();
        /*List<Tag> tags;
        tags = hs.listClasses();
        for (Tag tag : tags) {
            System.out.println(tag);
        }*/
        hs.obterSubclasses("Tag");
    }
}
