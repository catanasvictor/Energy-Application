import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import userService from '../../services/user.service';

const UsersList = () => {

  const [user, setUser] = useState([]);

  const init = () => {
    userService.getAll()
      .then(response => {
        console.log('Printing users data', response.data);
        setUser(response.data);
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  };

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    console.log('Printing id', id);
    userService.remove(id)
      .then(response => {
        console.log('User deleted successfully', response.data);
        init();
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  };

  if (sessionStorage.getItem("USER") === "ADMIN") {
    return (
      <div className="container">
        <h3>List of Users</h3>
        <hr />
        <div>
          <Link to="/user/add" className="btn btn-primary mb-2">Add User</Link>
          <table className="table table-bordered table-striped">
            <thead className="thead-dark">
              <tr>
                <th>Username</th>
                <th>Name</th>
                <th>Address</th>
                <th>Birthdate</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {
                user.map(user => (
                  <tr key={user.id}>
                    <td>{user.username}</td>
                    <td>{user.name}</td>
                    <td>{user.address}</td>
                    <td input type={Date}>{user.birthDate.substring(0, 10)}</td>
                    <td>
                      <Link className="btn btn-info" to={`/user/edit/${user.id}`}>Update</Link>
                      <button className="btn btn-danger ml-2" onClick={() => {
                        handleDelete(user.id);
                      }}>Delete</button>
                      <Link to={`/user/view-devices/${user.id}`} className="btn btn-warning ml-2">View Devices</Link>

                    </td>
                  </tr>
                ))
              }
            </tbody>
          </table>
          <Link to="/admin" className="btn btn-link mb-2">Back to Admin</Link>
          <p></p>
          <Link to="/login" className="btn btn-link mb-2">Logout</Link>
        </div>
      </div>
    );
  }
  else {
    return (<div>You are not logged in as an ADMIN.</div>);
  }

};

export default UsersList;