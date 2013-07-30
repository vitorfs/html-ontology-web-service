<%-- 
    Document   : index
    Created on : Jul 28, 2013, 11:05:59 PM
    Author     : vitorfs
--%>
<%@page import="java.util.List, wsclient.HtmlService, wsclient.HtmlService_Service, wsclient.Tag" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="top.jsp" %>
<h2>Consulta Ontologia <small>SparQL</small></h2>
<form class="form-inline" method="post" action="consulta_ontologia.jsp" style="margin-bottom: 20px">
  <input type="text" class="form-control" placeholder="Digite o nome da classe" name="classe" style="width: 250px">
  <button type="submit" class="btn btn-primary">Consultar</button>
</form>
<%String classe = request.getParameter("classe");
  if (classe != null) {%>
    <table class="table table-striped table-bordered">
        <thead>
            <tr>
                <th>Class Name</th>
                <th>NameSpace</th> 
                <th>URI</th>
            </tr>
        </thead>
        <tbody>
            <%
            HtmlService_Service ws = new HtmlService_Service();
            HtmlService hs = ws.getHtmlServicePort();


            List<Tag> tags = hs.obterSubclasses(classe);
            for (Tag tag : tags) {
                out.print("<tr><td>" + tag.getName() + "</td><td>" + tag.getNameSpace() + "</td><td>" + tag.getUri()+ "</td></tr>");
            }
            %>
        </tbody>
    </table>
<%}%>
<%@include file="bottom.jsp" %>