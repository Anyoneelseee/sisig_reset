from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_cors import CORS
from sqlalchemy import text
import logging

app = Flask(__name__)

# Enable CORS
CORS(app)

# Configuration for SQLAlchemy
app.config['SQLALCHEMY_DATABASE_URI'] = 'mysql+pymysql://notshinah:notshinah@localhost/porkysisig'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

logging.basicConfig(level=logging.DEBUG)

# Route for user login
@app.route('/login', methods=['POST'])
def login():
    data = request.json
    username = data.get('username')
    password = data.get('password')

    logging.debug(f"Attempting login with username: {username}")

    try:
        # Use text() to declare the SQL query
        user_query = text("SELECT * FROM users WHERE username = :username")
        user = db.session.execute(user_query, {'username': username}).fetchone()

        if user and user.password == password:
            logging.debug(f"User {username} logged in successfully.")
            return jsonify({"success": True, "message": "Login successful"})

        logging.warning(f"Failed login attempt for username: {username}")
        return jsonify({"success": False, "message": "Invalid credentials"})

    except Exception as e:
        logging.error(f"Error during login: {str(e)}")
        return jsonify({"success": False, "message": "Internal server error"}), 500

# Route for Reset Password
@app.route('/reset-password', methods=['POST'])
def reset_password():
    data = request.json
    username = data.get('username')
    new_password = data.get('newPassword')

    if not username or not new_password:
        return jsonify({"success": False, "message": "Username or new password missing"}), 400

    try:
        user_query = text("SELECT * FROM users WHERE username = :username")
        user = db.session.execute(user_query, {'username': username}).fetchone()

        if user:
            update_query = text("UPDATE users SET password = :new_password WHERE username = :username")
            db.session.execute(update_query, {'new_password': new_password, 'username': username})
            db.session.commit()

            return jsonify({"success": True, "message": "Password reset successful"})
        else:
            return jsonify({"success": False, "message": "User not found"}), 404

    except Exception as e:
        app.logger.error(f"Error resetting password for {username}: {e}")
        return jsonify({"success": False, "message": "Internal server error"}), 500

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=3000, debug=True)