# pkg-config --cflags --libs libmodbus
export LD_LIBRARY_PATH=$(pkg-config --cflags  libmodbus)

./cck7550s_12-reading
