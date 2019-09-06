

$(function() {

    // Area Chart
  Morris.Area({
        element: 'morris-area-chart',
        data: [{
            period: 'Launch',
            battery: 150,
            cpu: 20,
            memory: 15,
            network:27

        }, {
            period: 'Login',
              battery: 210,
            cpu: 40,
            memory: 30,
            network:90
        }, {
            period: 'Search',
             battery: 120,
            cpu: 20,
            memory: 16,
            network:90
        }, {
            period: 'Buy',
              battery: 230,
            cpu: 60,
            memory: 15,
            network:100
        }, {
            period: 'Cart',
              battery: 190,
            cpu: 75,
            memory: 16,
            network:60
        }, {
            period: 'Report',
             battery: 300,
            cpu: 8,
            memory: 121,
            network:50
        }],
        xkey: 'period',
        ykeys: ['battery', 'cpu', 'memory','network'],
        labels: ['battery', 'cpu', 'memory','network'],
        pointSize: 2,
        hideHover: 'auto',
        resize: true,
        parseTime: false
    });


    // Donut Chart
    Morris.Donut({
        element: 'morris-donut-chart',
        data: [{
            label: "Android",
            value: 50
        }, {
            label: "Iphone",
            value: 30
        }, {
            label: "Blackberry",
            value: 10
        }, {
            label: "Others",
            value: 10
        }],
        resize: true
    });

   

});
