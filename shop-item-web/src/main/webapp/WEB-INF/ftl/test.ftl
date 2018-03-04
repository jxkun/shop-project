<html>
<head> 
	<title>${title}</title>
</head>
<body>
	学生信息: <br/>
	学号: ${student.id}<br/>
	姓名: ${student.name}<br/>
	年龄: ${student.age}<br/>
	家庭住址: ${student.address}<br/>
<hr/><br/>	
	学生列表: <br/>
	<table border="1">
		<tr>
			<th>索引</th>
			<th>学号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>家庭住址</th>
		</tr>
	<#list stuList as stu>  
	<#if stu_index%3 == 0>
		<tr bgcolor="yellow">
	<#elseif stu_index%3==1>
		<tr bgcolor="blue">	
	<#else>
		<tr bgcolor="red">
	</#if>
			<td>${stu_index}</td>
			<td>${stu.id}</td>
			<td>${stu.name}</td>
			<td>${stu.age}</td>
			<td>${stu.address}</td>
		</tr>
	</#list>
	</table>
	<br/>
	日期类型处理：示例如下:<br/>
	<ol>
		<li>${date?date}</li>
		<li>${date?time}</li>
		<li>${date?datetime}</li>
		<li>${date?string("yyyy/MM/dd hh:mm:ss")}</li>
	</ol>
	<br/>
	对null值的处理(不处理null值,在使用freemarker会抛出异常):<br/>
	<ul>
		<li>null值处理(叹号后面设置null的默认值):${value!"默认值"}</li>
		<li>
			<#if value??>
			value不为null
			<#else>
			value为null值
			</#if>
		</li>
	</ul>
	<br/>
	include测试：<br/>
	<#include "hello.ftl">
</body>
</html>