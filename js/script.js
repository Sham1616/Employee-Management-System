// script.js - load, add, delete employees using fetch to /employees

async function loadEmployees() {
    try {
        const res = await fetch('employees?api=true', { headers: { 'Accept': 'application/json' } });
        const employees = await res.json();
        const tbody = document.getElementById('employeeData');
        if (!tbody) return; // if using index.html simple list, it may differ
        tbody.innerHTML = '';
        employees.forEach(emp => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${emp.id}</td>
                <td>${emp.name}</td>
                <td>${emp.department}</td>
                <td>${emp.salary}</td>
                <td>
                    <button data-id="${emp.id}" class="delete-btn">Delete</button>
                </td>
            `;
            tbody.appendChild(row);
        });

        // attach delete handlers
        document.querySelectorAll('.delete-btn').forEach(btn => {
            btn.addEventListener('click', async (e) => {
                const id = e.target.getAttribute('data-id');
                await deleteEmployee(id);
            });
        });
    } catch (err) {
        console.error('Failed to load employees', err);
    }
}

async function addEmployee(event) {
    if (event) event.preventDefault();
    const name = document.getElementById('name').value;
    const dept = document.getElementById('department').value;
    const salary = document.getElementById('salary').value;

    const payload = { name: name, department: dept, salary: Number(salary) };

    try {
        const res = await fetch('employees', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const json = await res.json();
        if (json.success) {
            loadEmployees();
            if (document.getElementById('employeeForm')) document.getElementById('employeeForm').reset();
        } else {
            alert('Failed to add employee');
        }
    } catch (err) {
        console.error(err);
        alert('Error adding employee');
    }
}

async function deleteEmployee(id) {
    try {
        const res = await fetch(`employees?id=${id}`, { method: 'DELETE' });
        const json = await res.json();
        if (json.success) loadEmployees();
        else alert('Delete failed');
    } catch (err) {
        console.error(err);
        alert('Error deleting employee');
    }
}

// hook up form if present
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('employeeForm')) {
        document.getElementById('employeeForm').addEventListener('submit', addEmployee);
    }
    loadEmployees();
});
