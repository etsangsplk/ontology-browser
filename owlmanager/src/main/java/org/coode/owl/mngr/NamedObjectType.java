/*
* Copyright (C) 2007, University of Manchester
*/
package org.coode.owl.mngr;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Nick Drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Jan 11, 2008<br><br>
 *
 *
 */
public enum NamedObjectType {

    ontologies ("Ontologies", "Ontology"),
    entities ("Entities", "Entity"),
    classes ("Classes", "Class"),
    objectproperties ("Object Properties", "Object Property"),
    dataproperties ("Data Properties", "Data Property"),
    annotationproperties ("Annotation Properties", "Annotation Property"),
    individuals ("Individuals", "Individual"),
    datatypes ("Datatypes", "Datatype");

    private static NamedObjectType[] entitySubTypes = new NamedObjectType[]{classes, objectproperties, dataproperties, annotationproperties, individuals, datatypes};

    private String plural;
    private String singular;

    NamedObjectType(String plural, String singular){
        this.plural = plural;
        this.singular = singular;
    }


    public String getPluralRendering(){
        return plural;
    }


    public String getSingularRendering() {
        return singular;
    }

    
    public static Set<String> getRenderings() {
        Set<String> renderings = new HashSet<>();
        for (NamedObjectType type : NamedObjectType.values()){
            renderings.add(type.toString());
        }
        return renderings;
    }


    public static NamedObjectType[] entitySubtypes() {
        return entitySubTypes;
    }


    public Class getCls() {
        switch(this){
            case classes: return OWLClass.class;
            case objectproperties: return OWLObjectProperty.class;
            case dataproperties: return OWLDataProperty.class;
            case annotationproperties: return OWLAnnotationProperty.class;
            case individuals: return OWLNamedIndividual.class;
            case datatypes: return OWLDatatype.class;
            case entities: return OWLEntity.class;
            case ontologies: return OWLOntology.class;
        }
        throw new RuntimeException("Unknown named object type: " + this);
    }


    public OWLEntity getOWLEntity(IRI iri, OWLDataFactory df){
        switch(this){
            case classes: return df.getOWLClass(iri);
            case objectproperties: df.getOWLObjectProperty(iri);
            case dataproperties: return df.getOWLDataProperty(iri);
            case annotationproperties: return df.getOWLAnnotationProperty(iri);
            case individuals: return df.getOWLNamedIndividual(iri);
            case datatypes: return df.getOWLDatatype(iri);
        }
        throw new RuntimeException("Unknown named object type: " + this);
    }


    public static NamedObjectType getType(OWLObject object){
        return getType(object.getClass());
    }

    
    public static NamedObjectType getType(Class<? extends OWLObject> cls){
        if (OWLClass.class.isAssignableFrom(cls)){
            return NamedObjectType.classes;
        }
        else if (OWLObjectProperty.class.isAssignableFrom(cls)){
            return NamedObjectType.objectproperties;
        }
        else if (OWLDataProperty.class.isAssignableFrom(cls)){
            return NamedObjectType.dataproperties;
        }
        else if (OWLAnnotationProperty.class.isAssignableFrom(cls)){
            return NamedObjectType.annotationproperties;
        }
        else if (OWLIndividual.class.isAssignableFrom(cls)){
            return NamedObjectType.individuals;
        }
        else if (OWLDatatype.class.isAssignableFrom(cls)){
            return NamedObjectType.datatypes;
        }
        else if (OWLOntology.class.isAssignableFrom(cls)){
            return NamedObjectType.ontologies;
        }
        throw new RuntimeException("Object type not known: " + cls);
    }

}
