<html>
<head>
    <title>Employees</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script src="${pageContext.request.contextPath}/js/script.js">
</script>
</head>
<body>
    <h2>Employees</h2>
    <form id="employeeForm">
        ID: <input type="number" id="empId" name="id"/>
        Name: <input type="text" id="empName" name="name"/>
        Department: <input type="text" id="empDept" name="department"/>
        Salary: <input type="number" id="empSalary" name="salary"/>
        <button type="submit">Add</button>
    </form>

    <table border="1">
        <thead>
            <tr>
                <th>ID</th><th>Name</th><th>Department</th><th>Salary</th><th>Action</th>
            </tr>
        </thead>
        <tbody id="employeeData"></tbody>
    </table>
</body>
</html>
