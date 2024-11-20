import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

private void registerUser(String email, String password) {
    // Hash the password before storing it
    String hashedPassword = hashPassword(password);

    // SQL query to insert the user into the database
    String sql = "INSERT INTO users (email, password) VALUES (?, ?)";

    try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, email);
        pstmt.setString(2, hashedPassword);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            // User registered successfully
            sendRegistrationNotification(email);
            // Here you can implement your own email verification logic
            sendVerificationEmail(email);
        } else {
            Toast.makeText(MainActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
        }
    } catch (SQLException e) {
        e.printStackTrace();
        Toast.makeText(MainActivity.this, "Database error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}

private void sendRegistrationNotification(String email) {
    NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    String channelId = "registration_channel";

    // Create notification channel for Android O and above
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(channelId, "Registration Notifications", NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(channel);
    }

    // Build the notification
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification) // Replace with your icon
            .setContentTitle("Registration Successful")
            .setContentText("Welcome " + email + ", you have successfully registered!")
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    // Show the notification
    notificationManager.notify(1, builder.build());
}

// Example of a method to send a verification email
private void sendVerificationEmail(String email) {
    // Implement your email sending logic here.
}
