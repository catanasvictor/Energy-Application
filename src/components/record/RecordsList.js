import axios from 'axios';
import { useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import recordService from '../../services/record.service.js';
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";

const RecordsList = () => {

  const [record, setRecord] = useState([]);
  const history = useHistory();

  const init = () => {
    recordService.getAll()
      .then(response => {
        console.log('Printing records data', response.data);
        setRecord(response.data);
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  useEffect(() => {
    init();
  }, []);

  const redirect = () => {
    if (sessionStorage.getItem("USER") === "ADMIN")
      history.push('/admin');
    else {
      history.push('/client');
    }
  }

  const handleDelete = (id) => {
    console.log('Printing id', id);
    recordService.remove(id)
      .then(response => {
        console.log('Record deleted successfully', response.data);
        init();
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  return (
    //init(),
    <div className="container">
      <h3>List of Records</h3>
      <hr />
      <div>
        <Link to="/record/add" className="btn btn-primary mb-2">Add Record</Link>
        <table className="table table-bordered table-striped">
          <thead className="thead-dark">
            <tr>
              <th>Timestamp</th>
              <th>Energy Consumption</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {
              record.map(record => (
                <tr key={record.id}>
                  <td>{record.timestamp}</td>
                  <td>{record.energyConsumption}</td>
                  <td>
                    <Link to={`/record/edit/${record.id}`} className="btn btn-info" >Update</Link>
                    <button className="btn btn-danger ml-2" onClick={() => { handleDelete(record.id); }}>Delete</button>
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table>
        <Link className="btn btn-link mb-2" onClick={() => { redirect(); }}>Back to Options</Link>
      </div>
    </div>
  );
}

export default RecordsList;