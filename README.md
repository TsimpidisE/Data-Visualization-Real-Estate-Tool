# Data Visualization Real Estate Tool

A Python-based command-line and web application for visualizing real estate data. Leverages Pandas, Matplotlib, and Plotly to generate interactive charts and maps. Ideal for analyzing housing market trends, price distributions, and geographic patterns.

## Table of Contents
- [Features](#features)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Usage](#usage)
- [Configuration](#configuration)
- [Examples](#examples)
- [Contributing](#contributing)
- [License](#license)
- [Author](#author)

## Features
- Load and preprocess CSV datasets of real estate listings
- Generate static and interactive visualizations:
  - Price distribution histograms
  - Time-series of median prices
  - Scatter plots for feature correlations
  - Geographic heatmaps on folium maps
- Export visualizations to PNG, HTML, and PDF
- Basic web interface using Flask to display interactive charts

## Project Structure

```
Data-Visualization-Real-Estate-Tool/
├── data/                      # Sample and raw CSV files
├── src/
│   ├── main.py                # Entry-point for CLI tool
│   ├── app.py                 # Flask web application
│   ├── viz/
│   │   ├── plots.py           # Functions to create Matplotlib charts
│   │   ├── interactive.py     # Plotly and Folium visualization logic
│   ├── preprocessing.py       # Data cleaning and transformation
│   └── config.py              # Configuration and constants
├── requirements.txt           # Python dependencies
├── Dockerfile                 # Containerization setup
├── README.md                  # Project documentation
└── LICENSE                    # License file
```

## Prerequisites
- Python 3.8 or higher
- pip or pipenv
- (Optional) Docker for containerized deployment

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/TsimpidisE/Data-Visualization-Real-Estate-Tool.git
   cd Data-Visualization-Real-Estate-Tool
   ```
2. Install dependencies:
   ```bash
   pip install -r requirements.txt
   ```
   Or with pipenv:
   ```bash
   pipenv install
   pipenv shell
   ```

## Configuration
- Edit `config.py` to set default dataset paths and output directories.
- For web mode, configure host and port in `app.py` or via environment variables.

## Usage
### CLI Mode
Generate a histogram of listing prices:
```bash
python src/main.py --input data/listings.csv --chart price_hist --output outputs/price_hist.png
```

### Web Mode
Start the Flask server to view interactive plots at `http://localhost:5000`:
```bash
python src/app.py
```

## Examples
- Run `python src/main.py --help` to see all chart types and options.
- View sample outputs in the `outputs/` directory.

## Contributing
Contributions welcome! Please fork the repo, create a feature branch, and submit a pull request. Include tests for new visualization functions.

## License
This project is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Author
Efthymios Tsimpidis  
GitHub: https://github.com/TsimpidisE
