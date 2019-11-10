const express = require('express')
const User = require('../models/user')
const router = new express.Router()
const auth = require('../middleware/auth')

router.post('/users', async (req, res) => {
    const user = new User(req.body)

    try{
        await user.save()
        const token = await user.generateAuthToken()
        res.status(201).send(token)
    }
    catch(e){
        res.status(400).send(e)
    }
})

router.post('/users/login', async (req, res) => {
    try{
        const user = await User.findByCredentials(req.body.username, req.body.password)
        const token = await user.generateAuthToken()
        res.send(token)
    }
    catch(e){
        res.status(400).send(e)
    }
})

router.post('/users/logout', auth, async (req, res) => {
    try{
        req.user.tokens = req.user.tokens.filter((token) => {
            return token.token != req.token
        })
        await req.user.save()

        res.send()
    }
    catch(e){
        res.status(500).send()
    }
})

router.get('/users/me', auth, async (req, res) => {
    //return userId can't return number
    res.send(req.user.userId + "")
})

router.post('/users/update', auth, async (req, res) => {
    const updates = Object.keys(req.body)
    const allowedUpdates = ['username', 'password']
    const isValidOperation = updates.every((update) => allowedUpdates.includes(update))
    const userId = req.user.userId
    
    if(!isValidOperation){
        return res.status(404).send({ errer: 'Invalid updates!' })
    }

    try{
        const user = await User.findOne({ userId })
        updates.forEach((update) => user[update] = req.body[update])
        await user.save()
        res.send("updates complete")
    }
    catch(e){
        res.status(400).send(e)
    }
})

module.exports = router