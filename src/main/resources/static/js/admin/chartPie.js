// Set new default font family and font color to mimic Bootstrap's default styling
Chart.defaults.global.defaultFontFamily = 'Nunito', '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
Chart.defaults.global.defaultFontColor = '#858796';

// Pie Chart Example
$(document).ready(() => {

    function getData() {
        $.ajax({
            url: "/api/admin/post/statisticPie",
            method: "get",
            dataType: "json"
        }).done(function (res) {
            writeChart(res);
        })
    }

    function writeChart(data) {
        let topics = data.map(el => el.topic.name);
        let percent = data.map(el => el.percent);
        let color = data.map(el => el.color);
        var ctx = document.getElementById("myPieChart").getContext('2d');
        var myPieChart = new Chart(ctx, {
            type: 'pie',
            data: {
                labels: topics,
                datasets: [{
                    data: percent,
                    backgroundColor: color,
                    hoverBackgroundColor: color,
                    hoverBorderColor: "rgba(234, 236, 244, 1)",
                }],
            },
            options: {
                maintainAspectRatio: false,
                tooltips: {
                    backgroundColor: "rgb(255,255,255)",
                    bodyFontColor: "#858796",
                    borderColor: '#dddfeb',
                    borderWidth: 1,
                    xPadding: 15,
                    yPadding: 15,
                    displayColors: false,
                    caretPadding: 10,
                },
                legend: {
                    // display: false
                    position: 'bottom',
                },
                cutoutPercentage: 80,
            },
        });
    }
    getData();
})

