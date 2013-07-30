<%-- 
    Document   : index
    Created on : Jul 28, 2013, 11:05:59 PM
    Author     : vitorfs
--%>
<%@page import="java.util.List, wsclient.HtmlService, wsclient.HtmlService_Service, wsclient.Tag" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="top.jsp" %>
<h2>Classes Assertadas</h2>
<table class="table table-striped table-bordered">
    <thead>
        <tr>
            <th>Class Name</th>
            <th>Equivalent To</th>
        </tr>
    </thead>
    <tbody>
        <%
        HtmlService_Service ws = new HtmlService_Service();
        HtmlService hs = ws.getHtmlServicePort();


        List<Tag> tags = hs.listClasses();
        for (Tag tag : tags) {
            out.print("<tr><td>" + tag.getName() + "</td><td>" + (tag.getEquiv() == null ? "" : tag.getEquiv()) + "</td></tr>");
        }
        %>
    </tbody>
</table>
<%@include file="bottom.jsp" %>