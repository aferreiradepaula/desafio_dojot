#include <stdio.h>
#ifndef _MSC_VER
#include <unistd.h>
#endif
#include <string.h>
#include <stdlib.h>
#include <errno.h>

#include <modbus.h>

int main(void)
{
modbus_t *ctx;
uint16_t tab_reg[64];
int rc;
int i;


//conect with the device
ctx = modbus_new_tcp("10.202.70.42", 502);
if (modbus_connect(ctx) == -1) {
    fprintf(stderr, "Connection failed: %s\n", modbus_strerror(errno));
    modbus_free(ctx);
    return -1;
} 


FILE *f = fopen("cck7550s_12.pmd", "w");

// read device registers
for (i = 1024; i <= 1150; i++){

	rc = modbus_read_registers(ctx, i, 2, tab_reg);
	if (rc == -1) {
    		fprintf(stderr, "%s\n", modbus_strerror(errno));
    		return -1; }
	else{
		fprintf(f, "%i : %f\n", i, modbus_get_float(tab_reg));
	}

	i ++;
}


fclose(f);




modbus_close(ctx);
modbus_free(ctx);

}

