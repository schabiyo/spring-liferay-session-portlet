<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

<portlet:actionURL var="addDataUrl">
	<portlet:param name="action" value="add-data" />
</portlet:actionURL>

<portlet:actionURL var="killInstanceUrl">
	<portlet:param name="action" value="kill-instance" />
</portlet:actionURL>
<nav>
	<p>
	<table class="table table-striped table-hover">
		<tr>
			<td colspan="2"><form action="${killInstanceUrl}" method="post"><input type="submit" value="Kill instance"></form></td>
		</tr>
		<tr class="info">
			<td><strong>Instance Guid </strong></td>
			<td>${instanceGuid}</td>
		</tr>
		<tr class="info">
			<td><strong>Portal instance # </strong></td>
			<td>${instanceId}</td>
		</tr>
		<tr class="info">
			<td><strong>Portal instance IP </strong></td>
			<td>${instanceIp}</td>
		</tr>
		<tr class="info">
			<td><strong>Portal instance Port </strong></td>
			<td>${instancePort}</td>
		</tr>
		<tr class="info">
			<td><strong>Session Id </strong></td>
			<td>${sessionId}</td>
		</tr>
		<tr>
			<td colspan="2"><strong>User's session data</strong></td>
		</tr>
	</table>
	</p>
	<table class="table table-striped">
			<thead>
			<tr>
				<th>Attribute Name</th>
				<th>Attribute Value</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${sessionAttributes}" var="attr">
				<tr>
					<td><c:out value="${attr.key}"/></td>
					<td><c:out value="${attr.value}"/></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	<form action="${addDataUrl}" method="post">
		<input type="text" name="dataKey"><br> 
		<input type="text"name="dataValue"><br> 
		<input type="submit">
	</form>

</nav>
