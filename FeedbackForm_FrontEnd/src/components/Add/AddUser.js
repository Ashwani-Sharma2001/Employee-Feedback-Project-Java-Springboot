import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Modal from "react-bootstrap/Modal";
import { addUser } from "../../utils/ApiFunction";
import { Link, useNavigate } from "react-router-dom";

const AddUser = () => {
  const [empId, setEmployeeId] = useState("");
  const [name, setEmployeeName] = useState("");
  const [email, setEmployeeEmail] = useState("");
  const [submitting, setSubmitting] = useState(false);
  const [showAlert, setShowAlert] = useState(false);
  const [validationError, setValidationError] = useState("");
  const [alertMessage, setAlertMessage] = useState("");
  const navigate = useNavigate();

  const showCustomAlert = (message) => {
    setAlertMessage(message);
    setShowAlert(true);
  };

  const hideCustomAlert = () => {
    setShowAlert(false);

    if (alertMessage.includes("successfully")) {
      navigate(-1);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!/^\d+$/.test(empId)) {
      setValidationError("Employee ID should only contain integers.");
      return;
    }
    if (!/^[a-zA-Z]+(?:\s[a-zA-Z]+){0,2}$/.test(name) || name.length > 30) {
      setValidationError(
        "Employee Name should only contain letters and at most two spaces, and be less than or equal to 30 characters."
      );
      return;
    }
    if (!/^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(email)) {
      setValidationError("Invalid email format.");
      return;
    }

    try {
      setSubmitting(true);

      const { success, message } = await addUser(empId, name, email);

      if (success) {
        console.log("User added successfully.....");
        showCustomAlert(message);
      } else {
        console.error("Failed to add user...");
        showCustomAlert(message);
      }
    } catch (error) {
      console.error("Error adding user:", error);
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="container">
      <div className="row p-3">
        <div className="col-6">
          <h2 className="float-start">Add User Form</h2>
        </div>
        <div className="col-6">
          <div className="float-end">
            <Link to={"/viewAllUser"}>
              <button className="btn btn-warning m-3">Back</button>
            </Link>
          </div>
        </div>
      </div>

      <div className="card bg-light p-4" style={{ border: "5px solid #ccc" }}>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3" controlId="formBasicEmpId">
            <Form.Label>Employee ID</Form.Label>
            <Form.Control
              // type="number"
              placeholder="Enter Employee Id"
              value={empId}
              onChange={(e) => {
                setEmployeeId(e.target.value);
                setValidationError("");
              }}
              required
            />
            {validationError && validationError.includes("Employee ID") && (
              <div className="text-danger">{validationError}</div>
            )}
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicName">
            <Form.Label>Employee Name</Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter Employee Name"
              value={name}
              onChange={(e) => {
                setEmployeeName(e.target.value);
                setValidationError("");
              }}
              required
            />
            {validationError && validationError.includes("Employee Name") && (
              <div className="text-danger">{validationError}</div>
            )}
          </Form.Group>

          <Form.Group className="mb-3" controlId="formBasicEmail">
            <Form.Label>Employee Email address</Form.Label>
            <Form.Control
              type="email"
              placeholder="Enter Employee email"
              value={email}
              onChange={(e) => {
                setEmployeeEmail(e.target.value);
                setValidationError("");
              }}
              required
            />
            {validationError && validationError.includes("email") && (
              <div className="text-danger">{validationError}</div>
            )}
          </Form.Group>

          <Button variant="primary" type="submit" disabled={submitting}>
            {submitting ? "Adding User..." : "Add User"}
          </Button>
        </Form>
      </div>

      <Modal
        show={showAlert}
        onHide={hideCustomAlert}
        centered
        backdrop="static"
        keyboard={false}
      >
        <Modal.Header closeButton>
          <Modal.Title>User Status</Modal.Title>
        </Modal.Header>
        <Modal.Body>{alertMessage}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={hideCustomAlert}>
            OK
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
};

export default AddUser;
