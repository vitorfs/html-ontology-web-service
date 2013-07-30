<%-- 
    Document   : index
    Created on : Jul 28, 2013, 11:05:59 PM
    Author     : vitorfs
--%>
<%@page import="java.util.List, wsclient.HtmlService, wsclient.HtmlService_Service, wsclient.Tag" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="top.jsp" %>
<h2>Validar Semântica</h2>
<form class="form" method="post" action="validar_semantica.jsp" style="margin-bottom: 20px">
    <select name="Type" class="form-control" style="margin-bottom: 20px">
        <option>MetaTag</option>
        <option>StructureTag</option>
        <option>Tabular</option>
        <option>List</option>
    </select>
    <textarea type="text" class="form-control" name="html" style="margin-bottom: 20px" rows="6"></textarea>
    <button type="submit" class="btn btn-primary">Validar</button>
</form>
<%String html = request.getParameter("html");
if (html != null) {
    String[] doc = html.split("\r\n");
    doc[0] = doc[0].replace("<", "").replace(">", "").replace(" ", "");
    //pai[0].charAt(0));
    String tag = Character.toUpperCase(doc[0].charAt(0)) + doc[0].substring(1);
    
    HtmlService_Service ws = new HtmlService_Service();
    HtmlService hs = ws.getHtmlServicePort();

    List<Tag> tags = hs.obterSubclasses(request.getParameter("Type"));
    String result = "";
    for (String s : doc) {
        String[] aux;
        aux = s.split(">");
        s = aux[0].replace("<", "").replace(">", "").replace(" ", "").toUpperCase();
        if (s.charAt(0) != '/') {
            int i;
            for (i = 0 ; i < tags.size() ; i++) {
                if (s.equals(tags.get(i).getName().toUpperCase())) {
                    break;
                }   
            }
            if (i == tags.size()) {
                result += ("<p class='text-danger'>" + s + " não é subclass de "+request.getParameter("Type")+"</p>");
            }
        }
    }
    
    if (result.length() > 0) {
        out.println(result);
    }
    else {
        out.println("<p class='text-success'>Semântica válida!</p>");
    }
    
}%>
<%@include file="bottom.jsp" %>