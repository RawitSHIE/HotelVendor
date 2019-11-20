const express = require('express')
require('./db/mongoose')
const userRouter = require('./routers/user')

const app = express()
const port = process.env.port || 3000

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
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        port:  {
            '$': 8080,
            '@enabled': 'true',
        },
        vipAddress: 'authservice',
        statusPageUrl: 'http://localhost:3000/',
        dataCenterInfo:  {
            '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
            name: 'MyOwn',
        },
        registerWithEureka: true,
        fetchRegistry: true
    },
    eureka: {
        host: 'https://hoteldiscovery.appspot.com/',
        port: 80,
        servicePath: '/eureka/apps/'
    }
});
eureka.logger.level('debug');
eureka.start(function(error){
    console.log(error || 'complete');
});