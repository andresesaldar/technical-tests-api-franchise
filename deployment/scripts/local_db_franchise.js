/* eslint-disable */

db = db.getSiblingDB('franchise_local')

db.createCollection('franchise');
db.createCollection('branch');
db.createCollection('product');

// ============================================
// FRANCHISES - Microprocessor Manufacturer
// ============================================
db.getCollection('franchise').insertMany([
    {
        "_id": ObjectId("673d8a1234567890abcdef01"),
        "slug": "intel-corporation",
        "name": "Intel Corporation"
    },
    {
        "_id": ObjectId("673d8a1234567890abcdef02"),
        "slug": "amd-technologies",
        "name": "AMD Technologies"
    },
    {
        "_id": ObjectId("673d8a1234567890abcdef03"),
        "slug": "nvidia-semiconductors",
        "name": "NVIDIA Semiconductors"
    }
]);

// ============================================
// BRANCHES
// ============================================
db.getCollection('branch').insertMany([
    // Intel Corporation
    {
        "_id": ObjectId("673d8b1234567890abcdef11"),
        "slug": "intel-silicon-valley",
        "name": "Intel Silicon Valley",
        "franchiseId": "673d8a1234567890abcdef01"
    },
    {
        "_id": ObjectId("673d8b1234567890abcdef12"),
        "slug": "intel-austin",
        "name": "Intel Austin",
        "franchiseId": "673d8a1234567890abcdef01"
    },
    {
        "_id": ObjectId("673d8b1234567890abcdef13"),
        "slug": "intel-chandler",
        "name": "Intel Chandler",
        "franchiseId": "673d8a1234567890abcdef01"
    },
    // AMD Technologies
    {
        "_id": ObjectId("673d8b1234567890abcdef21"),
        "slug": "amd-santa-clara",
        "name": "AMD Santa Clara",
        "franchiseId": "673d8a1234567890abcdef02"
    },
    {
        "_id": ObjectId("673d8b1234567890abcdef22"),
        "slug": "amd-markham",
        "name": "AMD Markham",
        "franchiseId": "673d8a1234567890abcdef02"
    },
    {
        "_id": ObjectId("673d8b1234567890abcdef23"),
        "slug": "amd-bangalore",
        "name": "AMD Bangalore",
        "franchiseId": "673d8a1234567890abcdef02"
    },
    // NVIDIA Semiconductors
    {
        "_id": ObjectId("673d8b1234567890abcdef31"),
        "slug": "nvidia-santa-clara",
        "name": "NVIDIA Santa Clara",
        "franchiseId": "673d8a1234567890abcdef03"
    },
    {
        "_id": ObjectId("673d8b1234567890abcdef32"),
        "slug": "nvidia-taipei",
        "name": "NVIDIA Taipei",
        "franchiseId": "673d8a1234567890abcdef03"
    }
]);

// ============================================
// PRODUCTS
// ============================================
db.getCollection("product").insertMany([
    // Intel Silicon Valley
    {
        "_id": ObjectId("673d8c1234567890abcde001"),
        "slug": "core-i9-14900k",
        "name": "Core i9-14900K",
        "stock": 250,
        "branchId": "673d8b1234567890abcdef11"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde002"),
        "slug": "core-i7-14700k",
        "name": "Core i7-14700K",
        "stock": 450,
        "branchId": "673d8b1234567890abcdef11"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde003"),
        "slug": "core-i5-14600k",
        "name": "Core i5-14600K",
        "stock": 680,
        "branchId": "673d8b1234567890abcdef11"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde004"),
        "slug": "xeon-platinum-8480",
        "name": "Xeon Platinum 8480",
        "stock": 120,
        "branchId": "673d8b1234567890abcdef11"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde005"),
        "slug": "celeron-g6900",
        "name": "Celeron G6900",
        "stock": 890,
        "branchId": "673d8b1234567890abcdef11"
    },
    // Intel Austin
    {
        "_id": ObjectId("673d8c1234567890abcde006"),
        "slug": "core-i9-13900k",
        "name": "Core i9-13900K",
        "stock": 320,
        "branchId": "673d8b1234567890abcdef12"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde007"),
        "slug": "core-i7-13700k",
        "name": "Core i7-13700K",
        "stock": 540,
        "branchId": "673d8b1234567890abcdef12"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde008"),
        "slug": "xeon-gold-6448y",
        "name": "Xeon Gold 6448Y",
        "stock": 180,
        "branchId": "673d8b1234567890abcdef12"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde009"),
        "slug": "pentium-gold-g7400",
        "name": "Pentium Gold G7400",
        "stock": 720,
        "branchId": "673d8b1234567890abcdef12"
    },
    // Intel Chandler
    {
        "_id": ObjectId("673d8c1234567890abcde010"),
        "slug": "core-ultra-9-285k",
        "name": "Core Ultra 9 285K",
        "stock": 150,
        "branchId": "673d8b1234567890abcdef13"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde011"),
        "slug": "core-ultra-7-265k",
        "name": "Core Ultra 7 265K",
        "stock": 380,
        "branchId": "673d8b1234567890abcdef13"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde012"),
        "slug": "core-i5-13400f",
        "name": "Core i5-13400F",
        "stock": 620,
        "branchId": "673d8b1234567890abcdef13"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde013"),
        "slug": "xeon-silver-4410y",
        "name": "Xeon Silver 4410Y",
        "stock": 210,
        "branchId": "673d8b1234567890abcdef13"
    },
    // AMD Santa Clara
    {
        "_id": ObjectId("673d8c1234567890abcde014"),
        "slug": "ryzen-9-7950x",
        "name": "Ryzen 9 7950X",
        "stock": 290,
        "branchId": "673d8b1234567890abcdef21"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde015"),
        "slug": "ryzen-7-7800x3d",
        "name": "Ryzen 7 7800X3D",
        "stock": 420,
        "branchId": "673d8b1234567890abcdef21"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde016"),
        "slug": "ryzen-5-7600x",
        "name": "Ryzen 5 7600X",
        "stock": 650,
        "branchId": "673d8b1234567890abcdef21"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde017"),
        "slug": "threadripper-pro-7995wx",
        "name": "Threadripper PRO 7995WX",
        "stock": 85,
        "branchId": "673d8b1234567890abcdef21"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde018"),
        "slug": "epyc-9754",
        "name": "EPYC 9754",
        "stock": 140,
        "branchId": "673d8b1234567890abcdef21"
    },
    // AMD Markham
    {
        "_id": ObjectId("673d8c1234567890abcde019"),
        "slug": "ryzen-9-7900x",
        "name": "Ryzen 9 7900X",
        "stock": 310,
        "branchId": "673d8b1234567890abcdef22"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde020"),
        "slug": "ryzen-7-7700x",
        "name": "Ryzen 7 7700X",
        "stock": 480,
        "branchId": "673d8b1234567890abcdef22"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde021"),
        "slug": "ryzen-5-5600x",
        "name": "Ryzen 5 5600X",
        "stock": 790,
        "branchId": "673d8b1234567890abcdef22"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde022"),
        "slug": "athlon-3000g",
        "name": "Athlon 3000G",
        "stock": 950,
        "branchId": "673d8b1234567890abcdef22"
    },
    // AMD Bangalore
    {
        "_id": ObjectId("673d8c1234567890abcde023"),
        "slug": "ryzen-9-5950x",
        "name": "Ryzen 9 5950X",
        "stock": 270,
        "branchId": "673d8b1234567890abcdef23"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde024"),
        "slug": "ryzen-7-5800x3d",
        "name": "Ryzen 7 5800X3D",
        "stock": 360,
        "branchId": "673d8b1234567890abcdef23"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde025"),
        "slug": "epyc-9554",
        "name": "EPYC 9554",
        "stock": 160,
        "branchId": "673d8b1234567890abcdef23"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde026"),
        "slug": "threadripper-3990x",
        "name": "Threadripper 3990X",
        "stock": 95,
        "branchId": "673d8b1234567890abcdef23"
    },
    // NVIDIA Santa Clara
    {
        "_id": ObjectId("673d8c1234567890abcde027"),
        "slug": "rtx-4090",
        "name": "RTX 4090",
        "stock": 180,
        "branchId": "673d8b1234567890abcdef31"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde028"),
        "slug": "rtx-4080-super",
        "name": "RTX 4080 Super",
        "stock": 340,
        "branchId": "673d8b1234567890abcdef31"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde029"),
        "slug": "rtx-4070-ti",
        "name": "RTX 4070 Ti",
        "stock": 520,
        "branchId": "673d8b1234567890abcdef31"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde030"),
        "slug": "rtx-4060-ti",
        "name": "RTX 4060 Ti",
        "stock": 680,
        "branchId": "673d8b1234567890abcdef31"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde031"),
        "slug": "a100-80gb",
        "name": "A100 80GB",
        "stock": 95,
        "branchId": "673d8b1234567890abcdef31"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde032"),
        "slug": "h100-pcie",
        "name": "H100 PCIe",
        "stock": 65,
        "branchId": "673d8b1234567890abcdef31"
    },
    // NVIDIA Taipei
    {
        "_id": ObjectId("673d8c1234567890abcde033"),
        "slug": "rtx-4070",
        "name": "RTX 4070",
        "stock": 450,
        "branchId": "673d8b1234567890abcdef32"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde034"),
        "slug": "rtx-4060",
        "name": "RTX 4060",
        "stock": 720,
        "branchId": "673d8b1234567890abcdef32"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde035"),
        "slug": "rtx-3090-ti",
        "name": "RTX 3090 Ti",
        "stock": 210,
        "branchId": "673d8b1234567890abcdef32"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde036"),
        "slug": "rtx-3060-ti",
        "name": "RTX 3060 Ti",
        "stock": 580,
        "branchId": "673d8b1234567890abcdef32"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde037"),
        "slug": "a40-48gb",
        "name": "A40 48GB",
        "stock": 125,
        "branchId": "673d8b1234567890abcdef32"
    },
    {
        "_id": ObjectId("673d8c1234567890abcde038"),
        "slug": "l40-48gb",
        "name": "L40 48GB",
        "stock": 110,
        "branchId": "673d8b1234567890abcdef32"
    }
]);

