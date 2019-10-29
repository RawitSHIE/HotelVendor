const mongoose = require('mongoose')

// connecting database
mongoose.connect('mongodb://127.0.0.1:27017/users-token-api', {
    useNewUrlParser: true,
    useCreateIndex: true,
    useFindAndModify: false
})