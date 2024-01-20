const path = require('path');

module.exports = {
  entry: './src/etwig-rrule.js', // Your main JavaScript file
  output: {
    filename: 'bundle.js', // The output bundle
    path: path.resolve(__dirname, 'dist'), // Output directory
  },
};