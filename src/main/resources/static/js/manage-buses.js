$(function () {
    $("#datepicker").datepicker({ dateFormat: 'yy-mm-dd' });
});

function editRow(element) {
    var deleteButton = element.parentNode.querySelector('.delete-btn');
    if (deleteButton) {
        deleteButton.style.display = 'none';
    }

    var rowId = element.id.split('-')[1];
    var row = document.querySelector('tr[data-bus-id="' + rowId + '"]');
    var cells = row.querySelectorAll('td[contenteditable="false"]');

    cells.forEach(function(cell) {
        cell.contentEditable = true;
        cell.dataset.originalValue = cell.innerText;
    });

    element.style.display = 'none';

    var saveButton = document.createElement('a');
    saveButton.href = '#';
    saveButton.className = 'save-btn';
    saveButton.innerHTML = '<i class="fas fa-save"></i> Save';
    saveButton.onclick = function() { saveRow(rowId); };
    element.parentNode.insertBefore(saveButton, element.nextSibling);

    var cancelButton = document.createElement('a');
    cancelButton.href = '#';
    cancelButton.className = 'cancel-btn';
    cancelButton.innerHTML = '<i class="fas fa-times"></i> Cancel';
    cancelButton.onclick = function() { cancelEdit(rowId); };
    element.parentNode.insertBefore(cancelButton, element.nextSibling);
}

function saveRow(rowId) {
    const row = document.querySelector('tr[data-bus-id="' + rowId + '"]');

    const deleteButton = row.querySelector('.delete-btn');
    if (deleteButton) {
        deleteButton.style.display = 'inline-block';
    }

    const cells = row.querySelectorAll('td[contenteditable="true"]');
    let formData = new URLSearchParams();

    cells.forEach(cell => {
        const fieldName = cell.id.split('-')[0];
        let value = cell.innerText.trim();
        formData.append(fieldName, value);
    });

    // Send ID as a query parameter
    const url = `/api/manage-buses/update?id=${encodeURIComponent(rowId)}`;

    fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
    })
    .then(result => console.log("Bus updated successfully:", result))
    .catch(error => console.error("Error updating bus:", error));

    // Restore edit button
    const saveButton = row.querySelector('.save-btn');
    const cancelButton = row.querySelector('.cancel-btn');
    saveButton.remove();
    cancelButton.remove();
    const editButton = row.querySelector('.edit-btn');
    editButton.style.display = 'inline-block';
}


function confirmDelete(busId) {
    console.log("Delete function called with busId:", busId);

    if (!confirm("Are you sure you want to delete this bus?")) {
        return false;
    }



    fetch(`/api/manage-buses/delete?id=${busId}`, {
        method: "DELETE"
//        headers: {
//            "X-CSRF-TOKEN": csrfToken
//        }
    }).then(response => {
        if (response.ok) {
            console.log("Bus deleted successfully");
            location.reload(); // Reload the page to reflect changes
        } else {
            console.error("Failed to delete bus");
        }
    }).catch(error => console.error("Error:", error));

    return false; // Prevent default <a> click behavior
}







function cancelEdit(rowId) {

    // Show the delete button again
        var row = document.querySelector('tr[data-bus-id="' + rowId + '"]');
        var deleteButton = row.querySelector('.delete-btn');
        if (deleteButton) {
            deleteButton.style.display = 'inline-block'; // Or 'block' depending on your CSS
        }
    var row = document.querySelector('tr[data-bus-id="' + rowId + '"]');
    var cells = row.querySelectorAll('td[contenteditable="true"]');

    cells.forEach(function(cell) {
        var fieldName = cell.id.split('-')[0];
        var originalValue = cell.dataset.originalValue;
        cell.innerText = originalValue;
        cell.contentEditable = false;
    });

    var saveButton = row.querySelector('.save-btn');
    var cancelButton = row.querySelector('.cancel-btn');
    saveButton.remove();
    cancelButton.remove();
    var editButton = row.querySelector('.edit-btn');
    editButton.style.display = 'inline-block';
}