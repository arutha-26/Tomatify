let array = [
    {
        id : 111,
        name : "bharata", 
        email : "bharatayasa@gmail.com",
    },
    {
        id : 222, 
        name : "widhi", 
        email : "widhin@gmail.com"
    }, 
    {
        id : 333, 
        name : "komang", 
        email : "komang@gmail.com"
    },
    {
        id : 444, 
        name : "hana", 
        email : "hana@gmail.com"
    }, 
    {
        id : 555, 
        name : "adel", 
        email : "adel@gmail.com"
    },
    {
        id : 666, 
        name : "utik", 
        email : "utik@gmail.com"
    }
]

module.exports = {
    read : (request, response) => {
        if (array.length > 0) {
            response.json({
                status  : true,
                data    : array,
                method  : request.method,
                URL     : request.url
            }); 
        } else {
            response.json({
                status  : false,
                message : 'No data found',
                method  : request.method,
                URL     : request.url
            });
        }
    },

    create : (request, response) => {
        array.push(request.body);
        response.send({
            status  : true,
            data    : array,
            massage : 'data user berhasil ditambah',
            method  : request.method,
            URL     : request.url
        });
    },

    update : (request, response) => {
        const id = request.params.usersid;
        array = array.map(user => {
            if (user.id == id) {
                user.name = request.body.name;
                user.email = request.body.email;
            }
            return user;
        }); 
        response.json({
            status  : true,
            data    : array,
            massage : 'data user berhasil diubah',
            method  : request.method,
            URL     : request.url
        });
    },

    delete : (request,response) => {
        let id = request.params.usersId;
        array = array.filter(item => item.id != id);
        response.send({
            status  : true,
            data    : array,
            message : 'Data berhasil dihapus',
            method  : request.method,
            URL     : request.url
        });
    }
}
