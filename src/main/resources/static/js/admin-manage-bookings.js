let originalValues = {};
let isEditing = false;

function enableEditMode(button) {
    if (isEditing) {
        alert("Please finish editing the current row before editing another row.");
        return;
    }

    isEditing = true;
    const rowId = button.getAttribute('data-booking-id');
    const row = document.querySelector(`tr[data-booking-id="${rowId}"]`);
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

    const otherRows = document.querySelectorAll('tr[data-booking-id]');
    otherRows.forEach(otherRow => {
        if (otherRow !== row && otherRow.querySelector('.save-btn').style.display === 'inline-block') {
            const otherRowId = otherRow.getAttribute('data-booking-id');
            cancelEditMode(otherRow.querySelector('.cancel-btn'), otherRowId);
        }
    });
}

function cancelEditMode(button, rowId) {
    const currentRowId = rowId || button.getAttribute('data-booking-id');
    const row = document.querySelector(`tr[data-booking-id="${currentRowId}"]`);
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

// ... (Previous JavaScript code for enableEditMode and cancelEditMode)

function saveRow(button) {
    const rowId = button.getAttribute('data-booking-id');
    const row = document.querySelector(`tr[data-booking-id="${rowId}"]`);
    const cells = row.querySelectorAll('td[contenteditable="true"]');
    let formData = new URLSearchParams();

    cells.forEach(cell => {
        const fieldName = cell.id.split('-')[0];
        let value = cell.innerText.trim();

        if (fieldName === 'bookingDate') {
            const [datePart, timePart] = value.split(' ');
            const [year, month, day] = datePart.split('-');
            const [hours, minutes] = timePart.split(':');

            const formattedDate = `${year}-${month}-${day}T${hours}:${minutes}:00`;
            value = formattedDate;
        }

        formData.append(fieldName, value);
    });

    const url = `/api/admin/manage-bookings/update?id=${encodeURIComponent(rowId)}`;

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
            return response.json();
        })
        .then(updatedBooking => {
            console.log("Booking updated successfully:", updatedBooking);
            showMessage('success', "Booking updated successfully!");
            cells.forEach(cell => {
                const fieldName = cell.id.split('-')[0];
                cell.innerText = updatedBooking[fieldName] || originalValues[rowId][fieldName];
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
            console.error("Error updating booking:", error);
            showMessage('error', "Error updating booking: " + error.message);
            isEditing = false;
        });
}

function deleteBooking(button) {
    const bookingId = button.getAttribute('data-booking-id');

    if (confirm("Are you sure you want to delete this booking?")) {
        fetch(`/api/admin/manage-bookings/delete?id=${bookingId}`, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    console.log("Booking deleted successfully");
                    const row = document.querySelector(`tr[data-booking-id="${bookingId}"]`);
                    row.remove();
                    showMessage('success', "Booking deleted successfully!");
                } else {
                    return response.text().then(err => {
                        throw new Error(err)
                    });
                }
            })
            .catch(error => {
                console.error("Error deleting booking:", error);
                showMessage('error', "Error deleting booking: " + error.message);
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