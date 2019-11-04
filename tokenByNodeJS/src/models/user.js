const mongoose = require('mongoose')
const jwt = require('../../node_modules/jsonwebtoken')

const userSchema = new mongoose.Schema({
    userId: {
        type: Number,
        required: true,
        unique: true,
    },
    username: {
        type: String,
        required: true,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    tokens: [{
        token: {
            type: String,
            require: true
        }
    }]
})

userSchema.statics.findByCredentials = async (username, password) => {
    const user = await User.findOne({ username })

    if(!user){
        throw new Error('Unable to login')
    }

    if(password != user.password){
        throw new Error('Unable to login')
    }

    return user
}

userSchema.methods.generateAuthToken = async function() {
    const user = this
    const token = jwt.sign({ _id: user._id.toString() }, 'canihavesometea')

    user.tokens = user.tokens.concat({ token })
    await user.save()
    return token
}

const User = mongoose.model('User', userSchema)

module.exports = User