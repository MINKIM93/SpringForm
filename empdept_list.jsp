<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "com.spring.springform.EmpDeptVO" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1" align="center">
	<tr>
		<th width = "80">EMPNO</th>
		<th width = "80">ENAME</th>
		<th width = "100">JOB</th>
		<th width = "80">DEPTNO</th>
		<th width = "80">DNAME</th>
		<th width = "80">LOC</th>
	</tr>
<%
ArrayList<EmpDeptVO> list = (ArrayList<EmpDeptVO>)request.getAttribute("empdept_list");
for (int i=0; i<list.size(); i++)
{
	EmpDeptVO empdeptvo = (EmpDeptVO)list.get(i);
%>	
	<tr>
		<td>&nbsp;<%=empdeptvo.getEmpno() %></td>
		<td>&nbsp;<%=empdeptvo.getEname() %></td>
		<td>&nbsp;<%=empdeptvo.getJob() %></td>
		<td>&nbsp;<%=empdeptvo.getDeptno() %></td>
		<td>&nbsp;<%=empdeptvo.getDname() %></td>
		<td>&nbsp;<%=empdeptvo.getLoc() %></td>

	</tr>
<%	
}
%>			
</body>
</html>
