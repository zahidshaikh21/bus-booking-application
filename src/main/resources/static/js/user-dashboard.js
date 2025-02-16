document.addEventListener("DOMContentLoaded", function () {
    let selectedSeats = [];
    let seatPrice = 0;
    let selectedBusId = null;


    function showSeatSelectionModal(button) {
        selectedBusId = button.getAttribute("data-bus-id");
        seatPrice = button.getAttribute("data-fare"); // Get fare from button attribute

        document.getElementById("modalBusId").value = selectedBusId;

        // Fetch seat details from API
        fetch(`/api/seat/bus/${selectedBusId}`)
            .then(response => response.json())
            .then(seats => {
                console.log(seats);
                generateSeatMap(seats); // Pass seats data to generate seat map
            })
            .catch(error => {
                console.error("Error fetching seats:", error);
                alert("Failed to load seats. Please try again.");
            });

        new bootstrap.Modal(document.getElementById("seatSelectionModal")).show();
    }

    function generateSeatMap(seats) {
        const seatMap = document.getElementById("seat-map");
        seatMap.innerHTML = ""; // Clear previous seats

        seats.forEach(seat => {
            let seatElement = document.createElement("div");
            seatElement.classList.add("seat");
            seatElement.textContent = seat.seatNumber;

            if (seat.status === "BOOKED") {
                seatElement.classList.add("booked");
                seatElement.textContent = "❌"; // Mark booked seats with a cross
            } else {
                seatElement.addEventListener("click", () => toggleSeatSelection(seat.seatNumber, seatElement));
            }

            seatMap.appendChild(seatElement);
        });
    }



    function toggleSeatSelection(seatNumber, seatElement) {
        if (selectedSeats.includes(seatNumber)) {
            selectedSeats = selectedSeats.filter(seat => seat !== seatNumber);
            seatElement.classList.remove("selected");
        } else {
            selectedSeats.push(seatNumber);
            seatElement.classList.add("selected");
        }


        // Ensure seatPrice is used properly
        document.getElementById("totalPrice").textContent = (selectedSeats.length * seatPrice).toFixed(2);

        document.getElementById("selected-seats-list").textContent = selectedSeats.join(", ");
        document.getElementById("selectedSeatNumbers").value = selectedSeats.join(",");
    }



    document.getElementById("confirmBooking").addEventListener("click", function () {
        if (selectedSeats.length === 0) {
            alert("Please select at least one seat.");
            return;
        }

        let amount = selectedSeats.length * seatPrice * 100; // Convert to paisa

        let options = {
            key: "rzp_test_4eMnzVJaUB2sKY",
            amount: amount,
            currency: "INR",
            name: "Bus Booking",
            description: "Ticket Payment",
            handler: async function (response) {
                alert("Payment Successful! Payment ID: " + response.razorpay_payment_id); // Debugging

                try {
                    let result = await fetch(`/api/user/dashboard/bookSeat/${selectedBusId}`, {
                        method: "POST",
                        headers: { "Content-Type": "application/x-www-form-urlencoded" },
                        body: new URLSearchParams({
                            seatNumbers: selectedSeats.join(","),
                            totalSeats: selectedSeats.length,
                            totalFare: (selectedSeats.length * seatPrice).toFixed(2)

                        })
                    });

                    if (result.ok) {
                        let data = await result.json();
                        alert("Booking successful! Your booking ID: " + data.bookingId);
                        window.location.href = "/api/user/dashboard";
                    } else {
                        let errorData = await result.json();
                        alert("Booking failed: " + errorData.message);
                        window.location.href = "/api/user/dashboard";
                    }
                } catch (error) {
                    alert("An error occurred: " + error.message);
                    window.location.href = "/api/user/dashboard";
                }
            },
            prefill: {
                name: "Zahid Shaikh",
                email: "shaikhzahid672@gmail.com",
                contact: "9702432125"
            },
            theme: { color: "#3399cc" }
        };

        let rzp = new Razorpay(options);
        rzp.open();
    });



    window.showSeatSelectionModal = showSeatSelectionModal;
});

document.getElementById("myBookingsBtn").addEventListener("click", function () {
                let myBookingsModal = new bootstrap.Modal(document.getElementById("myBookingsModal"));
                myBookingsModal.show();
            });
