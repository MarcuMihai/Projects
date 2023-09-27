import React from "react";
import Table from "../../commons/tables/table";


const columns = [
    {
        Header: 'Id',
        accessor: 'id',
    },
    {
        Header: 'Device\'s id',
        accessor: 'device.id',
    },
    {
        Header: 'Description',
        accessor: 'description',
    },
    {
        Header: 'Maximum value',
        accessor: 'maxValue',
    }
];

const filters = [
    {
        accessor: 'description',
    }
];

class SensorTable extends React.Component {

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

export default SensorTable;