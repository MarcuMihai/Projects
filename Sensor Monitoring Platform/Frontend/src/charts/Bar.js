import React, {Component} from "react";
import Chart from "react-google-charts";
import * as API_C from "../consumption/consumption-api";
import {Label} from "reactstrap";

class Bar extends Component {
    constructor(props) {
        super(props);
        this.state = {
            data: [],
            hours: [],
            days: [],
            values: [],
            daysNr: 1
        }
        this.fetchConsumption()
        this.handleChange = this.handleChange.bind(this);
    }

    fetchConsumption() {
        return API_C.getValues((result) => {
            if (result !== null) {
                this.setState({
                    data: result.apiResponse.body,
                });
            }
        });
    }

    handleChange(event) {
        this.setState({daysNr: event.target.value});
    }

    render() {
        let daysNumber = this.state.daysNr
        const data = []
        this.state.hours = this.state.data.map(x => new Date(x.timestamp).getUTCHours())
        this.state.days = this.state.data.map(x => Math.ceil((new Date().getTime() - new Date(x.timestamp).getTime())/(1000 * 60 * 60 * 24))+1)
        this.state.values = this.state.data.map(x => x.value)
        let initial = ['Hours']
        for (let i = daysNumber; i > 0; i--) {
            initial.push('Day' + (daysNumber - i + 1))
        }

        data.push(initial)

        for (let i = 0; i < 24; i++) {
            let x = [i]
            for (let j = 1; j <= daysNumber; j++) {
                x[j] = 0;
            }
            for (let h = 0; h < this.state.data.length; h++) {
                if (this.state.hours[h] === i && this.state.days[h]<=daysNumber) {
                    x[this.state.days[h]] += this.state.values[h]
                }
            }
            data.push(x)
        }

        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <Label> Days number: </Label>
                    <input type="number" value={this.state.daysNr} onChange={this.handleChange}/>
                </form>
                <div>
                    <Chart
                        width={'1100px'}
                        height={'600px'}
                        chartType="ColumnChart"
                        data={data}
                        options={{
                            title: 'History energy consumption',
                            chartArea: {width: '80%'},
                            vAxis: {
                                title: 'Value',
                                minValue: 0,
                            },
                            hAxis: {
                                ticks: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23],
                                title: 'Hour',
                                minValue: 0,
                                maxValue: 23,
                            },
                            isStacked: true,
                        }}
                    />
                </div>
            </div>
        )
    }
}

export default Bar;