package controller;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class HeaderlessObjectOutputStream extends ObjectOutputStream {

		public HeaderlessObjectOutputStream(OutputStream out) throws IOException {
			super(out);
		}

		@Override
		protected void writeStreamHeader() {
			// do nothing, don't write the header
		}
		

}

