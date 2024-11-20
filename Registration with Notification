FirebaseAuth mAuth = FirebaseAuth.getInstance();
String email = userEmailEdt.getText().toString();
String password = passwordEdt.getText().toString();

mAuth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener(this, task -> {
        if (task.isSuccessful()) {
            // User registered successfully
            FirebaseUser user = mAuth.getCurrentUser();
            sendRegistrationNotification(user.getEmail());
            
            // Send verification email
            user.sendEmailVerification()
                .addOnCompleteListener(verificationTask -> {
                    if (verificationTask.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Verification email sent!", Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(MainActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
        }
    });
