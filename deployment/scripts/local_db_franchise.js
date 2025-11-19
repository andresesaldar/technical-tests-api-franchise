/* eslint-disable */

db = db.getSiblingDB('franchise_local')

db.createCollection('product')

db.getCollection("product").insertMany([
    {
        "_id" : ObjectId("67ac8d0502ac823389072f8d"),
        "slug" : "enterprise-cpu-x9000",
        "name" : "Enterprise CPU X9000",
        "stock" : 150,
    },
    {
        "_id" : ObjectId("67ac8d0502ac823389072f8e"),
        "slug" : "server-grade-ram-128gb",
        "name" : "Server Grade RAM 128GB",
        "stock" : 200,
    },
    {
        "_id" : ObjectId("67ac8d0502ac823389072f8f"),
        "slug" : "network-processor-unit",
        "name" : "Network Processor Unit",
        "stock" : 95,
    },
    {
        "_id" : ObjectId("67ac8d0502ac823389072f90"),
        "slug" : "ai-accelerator-chip",
        "name" : "AI Accelerator Chip",
        "stock" : 75,
    },
    {
        "_id" : ObjectId("67ac8d0502ac823389072f91"),
        "slug" : "enterprise-storage-controller",
        "name" : "Enterprise Storage Controller",
        "stock" : 120,
    }
])