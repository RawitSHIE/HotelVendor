const express = require('express')
require('./db/mongoose')
const userRouter = require('./routers/user')

const app = express()
const port = process.env.port || 8080

app.use(express.json())
app.use(userRouter)

app.listen(port, () => {
    console.log('Server is up on port ' + port)
})

const Eureka = require('eureka-client').Eureka;

const eureka = new Eureka({
    instance: {
        app: 'authservice',
        instanceId: 'authservice',
        hostName: 'authservice-259910.appspot.com',
        ipAddr: '0.0.0.0/0',
        port:  {
            '$': 8080,
            '@enabled': 'true',
        },
        vipAddress: 'authservice',
        statusPageUrl: 'authservice-259910.appspot.com',
        dataCenterInfo:  {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
        registerWithEureka: true,
        fetchRegistry: true
    },
    eureka: {
        host: 'discovery-259910.appspot.com',
        port: 80,
        servicePath: '/eureka/apps/'
    }
});
eureka.logger.level('debug');
eureka.start(function(error){
    console.log(error || 'complete');
});