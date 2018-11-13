package vonzeeple.maplesyrup.utils;


import net.minecraftforge.fml.common.FMLLog;
import org.apache.logging.log4j.Level;
import vonzeeple.maplesyrup.MapleSyrup;


public class Logger {

	public static void log(Level logLevel, Object object)
	{
		FMLLog.log(MapleSyrup.NAME, logLevel, String.valueOf(object), new Object[0]);
	}
	
	public static void error(Object object)
	{
		log(Level.ERROR, object);
	}

	public static void info(Object object)
	{
		log(Level.INFO, object);
	}

	public static void warn(Object object)
	{
		log(Level.WARN, object);
	}
	
	
	
		
}
