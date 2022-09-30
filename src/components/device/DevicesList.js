import { useEffect, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import deviceService from '../../services/device.service';

const DevicesList = () => {

  const [device, setDevice] = useState([]);

  const history = useHistory();

  const init = () => {
    deviceService.getAll()
      .then(response => {
        console.log('Printing devices data', response.data);
        setDevice(response.data);
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  useEffect(() => {
    init();
  }, []);

  const handleDelete = (id) => {
    console.log('Printing id', id);
    deviceService.remove(id)
      .then(response => {
        console.log('Device deleted successfully', response.data);
        init();
      })
      .catch(error => {
        console.log('Something went wrong', error);
      })
  }

  const redirect = () => {
    if (sessionStorage.getItem("USER") === "ADMIN")
      history.push('/admin');
    else {
      history.push('/client');
    }
  }

  return (
    <div className="container">
      <h3>List of Devices</h3>
      <hr />
      <div>
        <Link to="/device/add" className="btn btn-primary mb-2">Add Device</Link>
        <table className="table table-bordered table-striped">
          <thead className="thead-dark">
            <tr>
              <th>Description</th>
              <th>Location</th>
              <th>Maximum Energy Consumption</th>
              <th>Baseline Energy Consumption</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {
              device.map(device => (
                <tr key={device.id}>
                  <td>{device.description}</td>
                  <td>{device.location}</td>
                  <td>{device.maximumEnergyConsumption}</td>
                  <td>{device.baselineEnergyConsumption}</td>
                  <td>
                    <Link to={`/device/edit/${device.id}`} className="btn btn-info">Update</Link>
                    <Link to={`/device/assign/${device.id}`} className="btn btn-warning ml-2">Assign</Link>
                    <button className="btn btn-danger ml-2" onClick={() => { handleDelete(device.id); }}>Delete</button>
                    <Link to={`/device/view-sensor/${device.id}`} className="btn btn-warning ml-2">View Sensor</Link>
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

export default DevicesList;