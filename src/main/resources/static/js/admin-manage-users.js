let originalValues = {};
let isEditing = false;

function enableEditMode(button) {
    if (isEditing) {
        alert("Please finish editing the current row before editing another row.");
        return;
    }

    isEditing = true;
    const rowId = button.getAttribute('data-user-id');
    const row = document.querySelector(`tr[data-user-id="${rowId}"]`);
    const cells = row.querySelectorAll('td[contenteditable="false"]');

    originalValues[rowId] = {};
    cells.forEach(cell => {
        const fieldName = cell.id.split('-')[0];
        originalValues[rowId][fieldName] = cell.innerText;
        cell.contentEditable = true;
    });

    button.style.display = 'none';
    row.querySelector('.save-btn').style.display = 'inline-block';
    row.querySelector('.cancel-btn').style.display = 'inline-block';
    row.querySelector('.delete-btn').style.display = 'none';

    const otherRows = document.querySelectorAll('tr[data-user-id]');
    otherRows.forEach(otherRow => {
        if (otherRow !== row && otherRow.querySelector('.save-btn').style.display === 'inline-block') {
            const otherRowId = otherRow.getAttribute('data-user-id');
            cancelEditMode(otherRow.querySelector('.cancel-btn'), otherRowId);
        }
    });
}

function cancelEditMode(button, rowId) {
    const currentRowId = rowId || button.getAttribute('data-user-id');
    const row = document.querySelector(`tr[data-user-id="${currentRowId}"]`);
    const cells = row.querySelectorAll('td[contenteditable="true"]');

    cells.forEach(cell => {
        const fieldName = cell.id.split('-')[0];
        cell.innerText = originalValues[currentRowId][fieldName];
        cell.contentEditable = false;
    });

    button.style.display = 'none';
    row.querySelector('.save-btn').style.display = 'none';
    row.querySelector('.edit-btn').style.display = 'inline-block';
    row.querySelector('.delete-btn').style.display = 'inline-block';

    isEditing = false;
}

function saveRow(button) {
    const rowId = button.getAttribute('data-user-id');
    const row = document.querySelector(`tr[data-user-id="${rowId}"]`);
    const cells = row.querySelectorAll('td[contenteditable="true"]');
    let formData = new URLSearchParams();

    cells.forEach(cell => {
        const fieldName = cell.id.split('-')[0];
        let value = cell.innerText.trim();
        formData.append(fieldName, value);
    });

    const url = `/api/admin/manage-users/update?id=${encodeURIComponent(rowId)}`;

    fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: formData
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(err => {
                    throw new Error(err)
                });
            }
            return response.json(); // Parse the response as JSON
        })
        .then(updatedUser => {
            console.log("User updated successfully:", updatedUser);
            showMessage('success', "User updated successfully!");
            cells.forEach(cell => {
                const fieldName = cell.id.split('-')[0];
                cell.innerText = updatedUser[fieldName] || originalValues[rowId][fieldName];
                cell.contentEditable = false;
            });
            button.style.display = 'none';
            row.querySelector('.save-btn').style.display = 'none';
            row.querySelector('.cancel-btn').style.display = 'none';
            row.querySelector('.edit-btn').style.display = 'inline-block';
            row.querySelector('.delete-btn').style.display = 'inline-block';

            isEditing = false;
        })
        .catch(error => {
            console.error("Error updating user:", error);
            showMessage('error', "Error updating user: " + error.message);
            isEditing = false;
        });
}

function deleteUser(button) {
    const userId = button.getAttribute('data-user-id');

    if (confirm("Are you sure you want to delete this user?")) {
        fetch(`/api/admin/manage-users/delete?id=${userId}`, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    console.log("User deleted successfully");
                    const row = document.querySelector(`tr[data-user-id="${userId}"]`);
                    row.remove();
                    showMessage('success', "User deleted successfully!");
                } else {
                    return response.text().then(err => {
                        throw new Error(err)
                    });
                }
            })
            .catch(error => {
                console.error("Error deleting user:", error);
                showMessage('error', "Error deleting user: " + error.message);
            });
        }
}


function showMessage(type, message) {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;

    const messageContainer = document.querySelector('.admin-container');
    messageContainer.insertBefore(alertDiv, messageContainer.firstChild);

    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}