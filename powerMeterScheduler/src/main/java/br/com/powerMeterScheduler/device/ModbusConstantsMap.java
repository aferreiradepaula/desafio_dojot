package br.com.powerMeterScheduler.device;

public enum ModbusConstantsMap {
	
	/** Versão de Firmware */
	Firmware(1024),
	/** Tensão Fase-Neutro A */
	TENSAO_FASE_NEUTRO_A(1026),
	/** Tensão Fase-Neutro B */
	TENSAO_FASE_NEUTRO_B(1028),
	/** Tensão Fase-Neutro C */
	TENSAO_FASE_NEUTRO_C(1030),
	/** Tensão Fase-Neutro Média */
	TENSAO_FASE_NEUTRO_MEDIA(1032),
	/** Tensão Fase-Fase A */
	TENSAO_FASE_FASE_A(1034),
	/** Tensão Fase-Fase B */
	TENSAO_FASE_FASE_B(1036),
	/** Tensão Fase-Fase C */
	TENSAO_FASE_FASE_C(1038),
	/** Tensão Fase-Fase Média */
	TENSAO_FASE_FASE_MEDIA(1040),
	/** Corrente Fase A */
	CORRENTE_FASE_A(1042),
	/** Corrente Fase B */
	CORRENTE_FASE_B(1044),
	/** Corrente Fase C */
	CORRENTE_FASE_C(1046),
	/** Corrente Total */
	CORRENTE_TOTAL(1048),
	/** Potência Ativa Fase A */
	POTENCIA_ATIVA_FASE_A(1050),
	/** Potência Ativa Fase B */
	POTENCIA_ATIVA_FASE_B(1052),
	/** Potência Ativa Fase C */
	POTENCIA_ATIVA_FASE_C(1054),
	/** Potência Ativa Total */
	POTENCIA_ATIVA_TOTAL(1056),
	/** Potência Reativa Fase A */
	POTENCIA_REATIVA_FASE_A(1058),
	/** Potência Reativa Fase B */
	POTENCIA_REATIVA_FASE_B(1060),
	/** Potência Reativa Fase C */
	POTENCIA_REATIVA_FASE_C(1062),
	/** Potência Reativa Total */
	POTENCIA_REATIVA_TOTAL(1064),
	/** Potência Aparente Fase A */
	POTENCIA_APARENTE_FASE_A(1066),
	/** Potência Aparente Fase B */
	POTENCIA_APARENTE_FASE_B(1068),
	/** Potência Aparente Fase C */
	POTENCIA_APARENTE_FASE_C(1070),
	/** Potência Aparente Total */
	POTENCIA_APARENTE_TOTAL(1072),
	/** Fator de Potência Fase A */
	FATOR_DE_POTENCIA_FASE_A(1074),
	/** Fator de Potência Fase B */
	FATOR_DE_POTENCIA_FASE_B(1076),
	/** Fator de Potência Fase C */
	FATOR_DE_POTENCIA_FASE_C(1078),
	/** Fator de Potência Total */
	FATOR_DE_POTENCIA_TOTAL(1080),
	/** Frequência */
	FREQUENCIA(1082),
	/** Totalizador kWh+ Fase A */
	TOTALIZADOR_KWH_POS_FASE_A(1084),
	/** Totalizador mWh+ Fase A */
	TOTALIZADOR_MWH_POS_FASE_A(1086),
	/** Totalizador kWh+ Fase B */
	TOTALIZADOR_KWH_POS_FASE_B(1088),
	/** Totalizador mWh+ Fase B */
	TOTALIZADOR_MWH_POS_FASE_B(1090),
	/** Totalizador kWh+ Fase C */
	TOTALIZADOR_KWH_POS_FASE_C(1092),
	/** Totalizador mWh+ Fase C */
	TOTALIZADOR_MWH_POS_FASE_C(1094),
	/** Totalizador kWh+ Total */
	TOTALIZADOR_KWH_POS_TOTAL(1096),
	/** Totalizador mWh+ Total */
	TOTALIZADOR_MWH_POS_TOTAL(1098),
	/** Totalizador kWh- Total */
	TOTALIZADOR_KWH_NEG_TOTAL(1100),
	/** Totalizador mWh- Total */
	TOTALIZADOR_MWH_NEG_TOTAL(1102),
	/** Totalizador kVArhIndQ4 Total */
	TOTALIZADOR_KVARHINDQ4_TOTAL(1104),
	/** Totalizador mVArhIndQ4 Total */
	TOTALIZADOR_MVARHINDQ4_TOTAL(1106),
	/** Totalizador kVArhCapQ1 Total */
	TOTALIZADOR_KVARHCAPQ1_TOTAL(1108),
	/** Totalizador mVArhCapQ1 Total */
	TOTALIZADOR_MVARHCAPQ1_TOTAL(1110),
	/** Totalizador kVArhQ2 Total */
	TOTALIZADOR_KVARHQ2_TOTAL(1112),
	/** Totalizador mVArhQ2 Total */
	TOTALIZADOR_MVARHQ2_TOTAL(1114),
	/** Totalizador kVArhQ3 Total */
	TOTALIZADOR_KVARHQ3_TOTAL(1116),
	/** Totalizador mVArhQ3 Total */
	TOTALIZADOR_MVARHQ3_TOTAL(1118),
	/** Ângulo entre Tensão A e Corrente A */
	ANGULO_TENSAO_A_CORRENTE_A(1120),
	/** Ângulo entre Tensão B e Corrente B */
	ANGULO_TENSAO_B_CORRENTE_B(1122),
	/** Ângulo entre Tensão C e Corrente C */
	ANGULO_TENSAO_C_CORRENTE_C(1124),
	/** Ângulo entre Tensão A e Tensão A */
	ANGULO_TENSAO_A_TENSAO_A(1126),
	/** Ângulo entre Tensão A e Tensão B */
	ANGULO_TENSAO_A_TENSAO_B(1128),
	/** Ângulo entre Tensão A e Tensão C */
	ANGULO_TENSAO_A_TENSAO_C(1130),
	/** THD Tensão A */
	THD_TENSAO_A(1132),
	/** THD Tensão B */
	THD_TENSAO_B(1134),
	/** THD Tensão C */
	THD_TENSAO_C(1136),
	/** THD Corrente A */
	THD_CORRENTE_A(1138),
	/** THD Corrente B */
	THD_CORRENTE_B(1140),
	/** THD Corrente C */
	THD_CORRENTE_C(1142),
	/** Primário TP */
	PRIMAIRO_TP(1144),
	/** Secundário TP */
	SECUNDARIO_TP(1146),
	/** Primário TC */
	PRIMARIO_TC(1148),
	/** Secundário TC */
	SECUNDARIO_TC(1150);
	
	private Integer modBusCommand;
	
	ModbusConstantsMap(Integer modBusCommand) {
		this.modBusCommand = modBusCommand;
	}
	
	/**
	 * Recupera o texto do comando
	 * @param command
	 * @return
	 */
	public static String stringFromCommand(int command) {
		for (ModbusConstantsMap mcm : ModbusConstantsMap.values()) {
			if (mcm.getModBusCommand() == command) {        
				return mcm.name();
			}
		}
		return null;
	}

	public Integer getModBusCommand() {
		return modBusCommand;
	}
}
