import React from "react";
import Table from "../../commons/tables/table";

const columns = [
    {
        Header: 'Id',
        accessor: 'id',
    },
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'Address',
        accessor: 'address',
    },
    {
        Header: 'Maximum energy consumption value',
        accessor: 'maximumEnergyConsumption',
    },
    {
        Header: 'Average energy consumption value',
        accessor: 'averageEnergyConsumption',
    },
    {
        Header: 'Sensor\'s id',
        accessor: 'sensor.id',
    }
];

const filters = [
    {
        accessor: 'id'
    }
];

class UserDevicesTable extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            tableData: this.props.tableData
        };
    }

    render() {
        return (
            <Table
                data={this.state.tableData}
                columns={columns}
                search={filters}
                pageSize={5}
            />
        )
    }
}

export default UserDevicesTable;