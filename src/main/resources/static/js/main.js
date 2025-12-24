// SEA Games Ticket System - Main JavaScript

document.addEventListener('DOMContentLoaded', function() {
    // Auto-hide alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Confirm before booking seat
    const bookingForms = document.querySelectorAll('.seat-btn:not(.seat-booked)');
    bookingForms.forEach(function(btn) {
        btn.closest('form')?.addEventListener('submit', function(e) {
            const seatInfo = btn.getAttribute('title') || 'ghế này';
            if (!confirm('Bạn có chắc muốn đặt ' + seatInfo + '?')) {
                e.preventDefault();
            }
        });
    });

    // Copy ticket code to clipboard
    const ticketCodes = document.querySelectorAll('.ticket-code');
    ticketCodes.forEach(function(code) {
        code.style.cursor = 'pointer';
        code.setAttribute('title', 'Click để sao chép');
        code.addEventListener('click', function() {
            navigator.clipboard.writeText(this.textContent).then(function() {
                alert('Đã sao chép mã vé!');
            });
        });
    });
});
