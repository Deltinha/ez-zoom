# EzZoom

A simple and intuitive zoom mod for Minecraft that allows you to zoom in and out with ease.

![Minecraft Version](https://img.shields.io/badge/Minecraft-1.21.7-brightgreen)
![Mod Loader](https://img.shields.io/badge/Mod%20Loader-Fabric-blue)
![License](https://img.shields.io/badge/License-MIT-green)

## Features

**Simple Zoom Controls**
- Press and hold `V` to zoom in (configurable keybind)
- Smooth zoom transition

**Adjustable Zoom Levels**
- Use scroll wheel while zooming to adjust zoom level
- Zoom range: 1.0x to 50.0x magnification
- Default zoom level: 3.0x


## Installation

### Requirements
- Minecraft 1.21.7
- Fabric Loader 0.16.14 or later
- Fabric API

### Steps
1. Download and install [Fabric Loader](https://fabricmc.net/use/installer/)
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) for Minecraft 1.21.7
3. Download the latest EzZoom release
4. Place both the Fabric API and EzZoom `.jar` files in your `mods` folder
5. Launch Minecraft with the Fabric profile

## Usage

### Basic Zoom
- **Hold `V`** to activate zoom (default key)
- **Release `V`** to return to normal view

### Adjusting Zoom Level
- **While holding `V`**, use the **scroll wheel** to adjust zoom level:
  - **Scroll up** = Zoom in further
  - **Scroll down** = Zoom out

### Keybind Configuration
You can change the zoom key in Minecraft's controls settings under the "EzZoom" category.

## Demo

![EzZoom Demo](media/demo.GIF)


## Building from Source

```bash
git clone https://github.com/your-username/EzZoom.git
cd EzZoom
./gradlew build
```

The built mod will be in `build/libs/ezzoom-1.0.0.jar`

## License

This project is licensed under the MIT License.
