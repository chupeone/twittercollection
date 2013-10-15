package example;

public class ConfigurationVariables {
private String tempTweetDir="tweetstoimport/";
private String finalTweetDir="C:\\Users\\Juan\\Dropbox\\MonitoredbyTwitterAggregator\\";
private String logDir="Logs/";
private String logExceptionDir="ExceptionLogs/";
private String logTerribleExceptionDir="TerribleExceptionLogs/";
private Integer batchSize=500;
public String getLogDir(){
	return this.logDir;
}
public String getLogExeptionDir(){
	return this.logExceptionDir;
}
public String getTerribleLogExeptionDir(){
	return this.logExceptionDir;
}
public String getTempTweetDir(){
	return this.tempTweetDir;
}
public String getFinalTweetDir(){
	return this.finalTweetDir;
}
public Integer getBatchSize(){
	return this.batchSize;
}
}
