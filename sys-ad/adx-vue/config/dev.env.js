'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  NODE_ENV: '"development"',
  // API_ROOT:'"http://101.200.35.202:8777"'
  API_ROOT:'"http://adx.dongle.com:8777"'
})
