/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vitorfs.model;

/**
 *
 * @author vitorfs
 */
public class Tag {
    private String name;
    private String equiv;
    private String uri;
    private String nameSpace;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return the equiv
     */
    public String getEquiv() {
        return equiv;
    }

    /**
     * @param equiv the equiv to set
     */
    public void setEquiv(String equiv) {
        this.equiv = equiv;
    }

    /**
     * @return the uri
     */
    public String getUri() {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /**
     * @return the nameSpace
     */
    public String getNameSpace() {
        return nameSpace;
    }

    /**
     * @param nameSpace the nameSpace to set
     */
    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
