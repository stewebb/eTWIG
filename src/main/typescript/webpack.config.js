
const path = require('path');

module.exports = {
  entry: './src/etwig-rrule.js', // Your main JavaScript file
  output: {
    filename: 'bundle.min.js', // The output bundle
    library: 'MyLibrary', //add this line to enable re-use
    path: path.resolve(__dirname, 'dist'), // Output directory
    libraryTarget: 'umd', // Universal Module Definition
    //globalObject: 'this'
  },
};

/*
const path = require('path');

module.exports = {
  entry: './src/etwig-rrule.ts', // Update the entry point to your TypeScript file
  output: {
    filename: 'bundle.min.js', // The output bundle
    path: path.resolve(__dirname, 'dist'), // Output directory
    library: 'Etwig', // Name of the global variable when included directly in browsers
    libraryTarget: 'umd', // Universal Module Definition, for browser and Node.js compatibility
    globalObject: 'this' // Ensures compatibility with both browser and Node.js environments
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/, // Regex to match TypeScript files
        use: 'ts-loader',
        exclude: /node_modules/
      }
    ]
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'] // Add '.ts' and '.tsx' as resolvable extensions.
  }
};
*/