import { useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import sensorService from '../../services/sensor.service';

const SensorsList = () => {

  const [sensor, setSensor] = useState([]);

  const history = useHistory();

  const init = () => {
    sensorService.getAll()
      .then(response => {
        console.log('Printing sensors data', response.data);
        setSensor(response.data);
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
    sensorService.remove(id)
      .then(response => {
        console.log('Sensor deleted successfully', response.data);
        init();
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  return (
    <div className="container">
      <h3>List of Sensors</h3>
      <hr />
      <div>
        <Link to="/sensor/add" className="btn btn-primary mb-2">Add Sensor</Link>
        <table className="table table-bordered table-striped">
          <thead className="thead-dark">
            <tr>
              <th>Sensor ID</th>
              <th>Description</th>
              <th>Maximum Value</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {
              sensor.map(sensor => (
                <tr key={sensor.id}>
                  <td>{sensor.id}</td>
                  <td>{sensor.description}</td>
                  <td>{sensor.maximumValue}</td>
                  <td>
                    <Link className="btn btn-info" to={`/sensor/edit/${sensor.id}`}>Update</Link>

                    <Link to={`/sensor/assign/${sensor.id}`} className="btn btn-warning ml-2">Assign</Link>

                    <button className="btn btn-danger ml-2" onClick={() => {
                      handleDelete(sensor.id);
                    }}>Delete</button>
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

export default SensorsList;