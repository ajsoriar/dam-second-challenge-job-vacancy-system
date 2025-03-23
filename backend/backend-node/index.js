// index.js
const express = require('express');
const db = require('./db');
const app = express();

app.use(express.json());

app.get('/api/empresas', (req, res) => {
    db.query('SELECT * FROM empresas', (err, results) => {
        if (err) {
            console.error('❌ Error SQL:', err);
            return res.status(500).json({ error: 'Error al consultar la base de datos' });
        }
        res.json(results);
    });
});

const PORT = 3000;
app.listen(PORT, () => {
  console.log(`Servidor Node escuchando en http://localhost:${PORT}`);
});