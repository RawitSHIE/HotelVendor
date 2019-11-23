const express = require('express')
require('./db/mongoose')
const userRouter = require('./routers/user')

const app = express()
const port = process.env.port || 8081

app.use(express.json())
app.use(userRouter)

app.listen(port, () => {
    console.log('Server is up on port ' + port)
})

const Eureka = require('eureka-client').Eureka;

const eureka = new Eureka({
    instance: {
        app: 'auth-service',
      instanceId: 'auth-service',
      hostName: 'https://authservice-259910.appspot.com',
      ipAddr: '127.0.0.1',
      port:  {
          '$': 8081,
          '@enabled': 'true',
      },
      vipAddress: 'auth-service',
      statusPageUrl: 'https://authservice-259910.appspot.com',
      dataCenterInfo:  {
          '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          name: 'MyOwn',
      },
      registerWithEureka: true,
      fetchRegistry: true
    }
});
eureka.logger.level('debug');
eureka.start(function(error){
    console.log(error || 'complete');
});