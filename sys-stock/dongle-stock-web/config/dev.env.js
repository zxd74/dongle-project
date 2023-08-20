'use strict'
const merge = require('webpack-merge')
const prodEnv = require('./prod.env')

module.exports = merge(prodEnv, {
  API_ROOT:'"http://localhost:8888"',
  NODE_ENV: '"development"'
})
