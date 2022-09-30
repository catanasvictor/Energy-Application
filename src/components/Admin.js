import { Link } from 'react-router-dom';

const Admin = () => {

    if (sessionStorage.getItem("USER") === "ADMIN") {
        return (
            <div className="container">
                <h3>Welcome Admin!</h3>
                <hr />
                <div>
                    <Link to="/user" className="btn btn-primary mb-2">Manage Users</Link>
                    <tab> </tab>
                    <Link to="/device" className="btn btn-primary mb-2">Manage Devices</Link>
                    <tab> </tab>
                    <Link to="/sensor" className="btn btn-primary mb-2">Manage Sensors</Link>
                    <tab> </tab>
                    <Link to="/record" className="btn btn-primary mb-2">Manage Records</Link>
                    <p></p>
                    <Link to="/login" className="btn btn-link mb-2">Logout</Link>
                </div>
            </div>
        );
    }
    else {
        return (<div>You are not logged in as an ADMIN.</div>);
    }
}

export default Admin;