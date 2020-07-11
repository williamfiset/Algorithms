package vEB;

public class VEBNode
{	
	public int universeSize;
	public int min;
	public int max;
	public VEBNode summary;
	public VEBNode[] cluster;
	
	public VEBNode(int universeSize)
	{
		this.universeSize = universeSize;
		min = VEBTree.NULL;
		max = VEBTree.NULL;	
		
		/* Allocate the summary and cluster children. */
		initializeChildren(universeSize);
	}
	
	private void initializeChildren(int universeSize)
	{
		if(universeSize <= VEBTree.BASE_SIZE)
		{
			summary = null;
			cluster = null;
		}
		else
		{
			int childUnivereSize = higherSquareRoot();
			
			summary = new VEBNode(childUnivereSize);
			cluster = new VEBNode[childUnivereSize];
			
			for(int i = 0; i < childUnivereSize; i++)
			{
				cluster[i] = new VEBNode(childUnivereSize);
			}
		} 
	}
	
	/*
	 * Returns the value of the most significant bits of x.
	 */
	private int higherSquareRoot()
	{
		return (int)Math.pow(2, Math.ceil((Math.log10(universeSize) / Math.log10(2)) / 2));
	}
}
